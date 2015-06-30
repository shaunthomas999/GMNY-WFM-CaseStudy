Accounts.validateLoginAttempt (attemptInfo) ->
	if attemptInfo.type is "resume"
		console.log "Auto-login prevented"
		return false 
	else 
		return true

# ## Meteor.methods
#
Meteor.methods

	authenticateUser: (customerId, password) ->
		authObj = {}
		authObj.data = {}
		authObj.data.customerId = customerId
		authObj.data.password = password
		console.log "Data to send to proxy : " + JSON.stringify authObj
		responseValue = null
		try
			result = HTTP.post 'http://localhost:8081/bpm/authenticateUser', authObj
			authResult = JSON.parse(result.content)
			console.log "GMNYclientApp : authentication.coffee > " + "Result received : " + result.content
			if authResult.outcome is "success"
				meteorUserCursor = Meteor.users.find({username: customerId})
				if not meteorUserCursor.count()
					Accounts.createUser({username: customerId, password: password})
					console.log "GMNYclientApp : authentication.coffee > " +"Created new user #{customerId} in Meteor.users collection"
				else
					console.log "GMNYclientApp : authentication.coffee > " +"Customer already exists in our list"
				console.log "GMNYclientApp : authentication.coffee > " +"Customer type : " + authResult.customerType
				responseValue = authResult.customerType
			else
				console.error "Cannot authenticate user"
				responseValue = null
		catch err
			console.error "Error in /bpm/authenticateUser : \n" + err
		
		return responseValue

	# This method is invoked in push-ad flow for to get customer object
	#
	# @method authenticateUserWithCIDFromPushAd
	# @param {Number} customerID customerID of the user
	# @return {Object} Customer document
	authenticateUserWithCIDFromPushAd: (customerID) ->
		customer = Customers.findOne({CID: customerID})
		return customer