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
			message = "Your loan application is #{@query.loanApplicationStatus}"
		else if @query.loanApplicationStatus is "declined. Please contact bank for more information."
			message = "Your loan application is #{@query.loanApplicationStatus}"
		else if @query.loanApplicationStatus is "terminate"
			message = "Your loan application is terminated. Please contact bank for more information."
		else
			message = "Loan application status has changed. Please contact bank for more information."
		
		Meteor.call('sendPushNotification_CIDs', @query.customerId, title, message)

		return console.log "push ads sent"