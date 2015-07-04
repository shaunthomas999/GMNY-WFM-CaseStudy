HTTP.methods
	# Sends push ads
	# 
	# @method sendPushAds
	# @param {Object} data Rest request body
	'send_pushAds_api/sendPushAds': (data)->
		console.log "Push-ad send to customerId : " + @query.customerId + "loanApplicationStatus" + @query.loanApplicationStatus
		title = "Loan Application Status"
		message = "Loan application for customerId #{@query.customerId} is #{@query.loanApplicationStatus}"
		Meteor.call('sendPushNotification_CIDs', @query.customerId, title, message)

		return console.log "push ads sent"