if Meteor.isServer
  Meteor.methods
    privateCustomerRegistration: (privateCustomerObj) ->
      console.log 'Ready to send private customer registration details for ' + privateCustomerObj.firstName
      HTTP.post 'http://localhost:8081/registerCustomer', privateCustomerObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return

    businessCustomerRegistration: (businessCustomerObj) ->
      console.log 'Ready to send business customer registration details for ' + businessCustomerObj.orgName
      HTTP.post 'http://localhost:8081/registerCustomer', businessCustomerObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
      return

    authenticateUser: (customerId, password) ->
      console.log 'Going to authenticate user with customer ID : ' + customerId