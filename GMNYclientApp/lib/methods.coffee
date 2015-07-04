if Meteor.isServer
  Meteor.methods
    customerRegistration: (customerObj) ->
      console.log 'Ready to send customer registration details : ' + JSON.stringify customerObj

      if customerObj.dateOfBirth is ""
        dateOfBirth = ""
      else
        dateOfBirth = customerObj.dateOfBirth+"T18:25:43.511Z"

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
                    "dateOfBirth" : {"value" : dateOfBirth, "type": "Date"},
                    "gender" : {"value" : customerObj.gender, "type": "String"},
                    "customerType" : {"value" : customerObj.customerType, "type": "String"}
                  }
                }

      requestBody = {
                    "data": message,
                    "headers": {'Content-Type': 'application/json'}
                    }

      console.log "Request body : " + JSON.stringify requestBody

      HTTP.post 'http://localhost:8081/engine-rest/process-definition/key/customer-registration/start', requestBody, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return

    loanApplication: (loanObj) ->
      console.log 'Ready to send loan application ' + JSON.stringify loanObj

      message = { "variables":
                  {
                    "amount" : {"value" : loanObj.amount, "type": "Long"},
                    "period" : {"value" : loanObj.period, "type": "String"},
                    "occupation" : {"value" : loanObj.occupation, "type": "String"},
                    "annualSalary" : {"value" : loanObj.annualSalary, "type": "Integer"},
                    "loanType" : {"value" : loanObj.loanType, "type": "String"}
                  }
                }

      requestBody = {
                    "data": message,
                    "headers": {'Content-Type': 'application/json'}
                    }

      console.log "Request body : " + JSON.stringify requestBody
      loanApplicationURL = 'http://localhost:8081/engine-rest/process-definition/key/loan-approval/start'
      console.log loanApplicationURL
      HTTP.post loanApplicationURL, requestBody, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return