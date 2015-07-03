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
		#authObj = {}
		#authObj.data = {}
		#authObj.data.customerId = customerId
		#authObj.data.password = password
		console.log "Data to send to proxy : " + customerId + " - " + password
		responseValue = null
		try
			checkPasswordURL = "http://localhost:8081/gmny/client/api/checkPassword/#{customerId}/#{password}"
			console.log checkPasswordURL
			result = HTTP.get checkPasswordURL
			authResult = result.content
			console.log "GMNYclientApp : authentication.coffee > " + "Result received : " + result.content
			
			if authResult is "pass"
				meteorUserCursor = Meteor.users.find({username: customerId})
				if not meteorUserCursor.count()
					Accounts.createUser({username: customerId, password: password})
					console.log "GMNYclientApp : authentication.coffee > " +"Created new user #{customerId} in Meteor.users collection"
				else
					console.log "GMNYclientApp : authentication.coffee > " +"Customer already exists in our list"

				# Get the customer details from BPM app
				getCustomerURL = "http://localhost:8081/gmny/client/api/getCustomer/#{customerId}"
				console.log getCustomerURL
				result = HTTP.get getCustomerURL

				customerObj = JSON.parse result.content

				console.log "Customer object received : " + JSON.stringify customerObj
				console.log "GMNYclientApp : authentication.coffee > " +"Customer type : " + customerObj.customerType

				responseValue = customerObj
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