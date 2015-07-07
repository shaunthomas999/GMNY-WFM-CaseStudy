HTTP.methods
	# Sends push ads
	# 
	# @method sendPushAds
	# @param {Object} data Rest request body
	'send_pushAds_api/sendPushAds': (data)->
		console.log "Push-ad send to customerId : " + @query.customerId + "loanApplicationStatus" + @query.loanApplicationStatus
		title = "Update on loan application"
		if @query.loanApplicationStatus is "applied"
			message = "Your application is being processed!"
		else if @query.loanApplicationStatus is "accepted"
			message = "We are pleased to tell you that your loan application is accepted!"
		else if @query.loanApplicationStatus is "declined"
			message = "We are sorry to tell you that your loan application is declined."
		else if @query.loanApplicationStatus is "terminate"
			message = "Your application is now terminated."
		else
			message = "Loan application status has changed. Please contact bank for more information."
		
		Meteor.call('sendPushNotification_CIDs', @query.customerId, title, message)

		return console.log "push ads sent"