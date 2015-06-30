if Meteor.isServer
  Meteor.methods
    customerRegistration: (customerObj) ->
      console.log 'Ready to send customer registration details : ' + JSON.stringify customerObj
      HTTP.post 'http://localhost:8081/process-definition/CustomerRegStart/submit-form', customerObj, (err, result) ->
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