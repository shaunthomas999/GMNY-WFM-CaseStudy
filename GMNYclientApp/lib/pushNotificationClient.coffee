root = global ? window

# The callback that will be executed on receiving push-ad
# 
# @method root.notificationCallback
# @param {Object} payload Payload received in push-ad
root.notificationCallback = (payload) ->
	console.log "\n\nnotificationCallback : #{JSON.stringify(payload)}\n\n"
	Session.set("deliveryPrice_Pushad", payload.deliveryPrice)
	Session.set("selectedBundleID", parseInt(payload.bundleId))
	customerId = parseInt(payload.CID)
	if Session.get("currentuser") is null or Session.get("currentuser") is undefined
		Meteor.call 'authenticateUserWithCIDFromPushAd', customerId, (error, result) ->
			Session.set("currentuser", result)
			console.log "Customer added to session - CID : " + result?.CID
	Router.go('bundleList')

# Push-ad reception handler
# 
# @method root.App
# @param {Object} notificationClient Instance of notificationClient object
root.App = notificationClient: new NotificationClient(
	senderId: "331369739623"
	gcmAuthorization: "AIzaSyD-93rIau2YoapukfhI3G52XFfNDZ-Ab2s"
	registeredCallback: ->
		console.log "Ready to accept push notifications"
	messageHandler: (payload, foreground, coldstart) ->
		console.log "Payload received : " + JSON.stringify(payload)
		return null  unless payload
		if foreground and not coldstart
			navigator.notification.alert payload.message, notificationCallback(payload), payload.title
		else
			window.plugin.notification.local.add _.extend(notificationCallback(payload),
			  message: payload.message
			  title: payload.title
			  autoCancel: true
			)
		return	
)