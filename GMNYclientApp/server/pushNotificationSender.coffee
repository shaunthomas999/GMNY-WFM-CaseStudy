Meteor.methods
	'sendPushNotification_CIDs': (customerId, title, message) ->
		console.log "Customer choosen to send push notification : " + customerId
		try
			#- Get the userIds corresponding to CIDs from Meteor.users collection
			usersArray = []
			user = Meteor.users.findOne({username: customerId})
			if user isnt undefined
				usersArray.push(user)
			else
				throw "User undefined"

			console.log "Going to send push notification"
			console.log "**Title - #{title} \n **Message - #{message}"
		
			#- Send push notifications to customers with given userIds
			App.notificationClient.sendNotification usersArray, {title: title, message: message}
		catch error
			console.error "ERROR : sendPushNotification_CIDs : Some problem in sending out push notifications \n " + error