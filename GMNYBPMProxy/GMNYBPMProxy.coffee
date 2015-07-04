root = window ? global

# ## Meteor.isClient
if root.Meteor.isClient
  console.log "Client loaded"

# ## Meteor.isServer
if root.Meteor.isServer
	Meteor.startup ->
		console.log "*** GMNYBPMProxy has started up ***"

	# - HTTP.methods
	HTTP.methods
		'bpm/authenticateUser': (data) ->
			console.log "GMNYBPMProxy > " + "Received data : " + JSON.stringify(data)
			result = {}
			if data.customerId is "1" and data.password is "simple"
				result.outcome = "success"
				result.customerType = "private"
			else if data.customerId is "2" and data.password is "simple"
				result.outcome = "success"
				result.customerType = "business"
			else
				result.outcome = "failure"

			@setContentType('text/json')
			console.log "GMNYBPMProxy > " + "Result to send to client app server : " + JSON.stringify(result)
			return JSON.stringify(result)

		'connected_car_api/getCarsInLocation': (data) ->
			customerIdList = null
			console.log "OEMConnectedCar > " + "getCarsInLocation request handler"
			locationName = @query.name
			console.log "OEMConnectedCar > " + "location to look for : " + locationName
			if locationName is "Spandau"
				customerIdList = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
			else if locationName is "Charlottenburg-Wilmersdorf"
				customerIdList = [16,17,18,19,20]
			else if locationName is "Steglitz-Zehlendorf"
				customerIdList = [21,22,23,24,25,26,27,28,29,30]
			else if locationName is "Tempelhof-Schöneberg"
				customerIdList = [31,32,33,34,35,36,37,38,39,40]
			else
				customerIdList = [41,42,43,44,45,46,47,48,49,50,51,52]

			@setContentType('text/json')
			console.log "Result to send back : " + JSON.stringify(customerIdList)
			return JSON.stringify(customerIdList)