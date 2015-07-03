if Meteor.isServer
  Meteor.methods
    customerRegistration: (customerObj) ->
      console.log 'Ready to send customer registration details : ' + JSON.stringify customerObj

      message = { "variables":
                  {
                    "orgName" : {"value" : customerObj.orgName, "type": "String"},
                    "firstname" : {"value" : customerObj.firstName, "type": "String"},
                    "lastname" : {"value" : customerObj.lastName, "type": "String"},
                    "email" : {"value" : customerObj.email, "type": "String"},
                    "phoneNumber" : {"value" : customerObj.contactNumber, "type": "String"},
                    "street" : {"value" : customerObj.streetName, "type": "String"},
                    "streetNumber" : {"value" : customerObj.streetNum, "type": "String"},
                    "zipCode" : {"value" : customerObj.pincode, "type": "String"},
                    "city" : {"value" : customerObj.city, "type": "String"},
                    "dateOfBirth" : {"value" : customerObj.dateOfBirth+"T18:25:43.511Z", "type": "Date"},
                    "gender" : {"value" : customerObj.gender, "type": "String"},
                    "customerType" : {"value" : customerObj.customerType, "type": "String"}
                  }
                }

      requestBody = {
                    "data": message,
                    "headers": {'Content-Type': 'application/json'}
                    }

      console.log "Request body : " + requestBody

      HTTP.post 'http://localhost:8081/engine-rest/process-definition/key/customer-registration/start', requestBody, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return

    loanApplication: (loanObj) ->
      console.log 'Ready to send loan application ' + JSON.stringify loanObj
      HTTP.post 'http://localhost:8081/applyLoan', loanObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return