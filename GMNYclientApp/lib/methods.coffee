if Meteor.isServer
  Meteor.methods
    customerRegistration: (customerObj) ->
      console.log 'Ready to send customer registration details for ' + customerObj.firstName
      console.log JSON.stringify customerObj
      HTTP.post 'http://localhost:8081/registerCustomer', customerObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return