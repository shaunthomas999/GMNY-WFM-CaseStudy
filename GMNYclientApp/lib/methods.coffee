if Meteor.isServer
  Meteor.methods
    customerRegistration: (customerObj) ->
      console.log 'Ready to send customer registration details : ' + JSON.stringify customerObj
      HTTP.post 'http://localhost:8081/process-definition/CustomerRegStart/submit-form', customerObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return

      meteorUserCursor = Meteor.users.find({username: customerObj.customerId})
      if not meteorUserCursor.count()
        Accounts.createUser({username: customerId, password: password})
        console.log "GMNYclientApp : authentication.coffee > " +"Created new user #{customerId} in Meteor.users collection"
        #- Add customerType to the users collection which is required for various functionalities
        Meteor.users.update({username: email}, {$set: {customerType: customerObj.customerType}})
      else
        console.log "GMNYclientApp : authentication.coffee > " +"Customer already exists in our list"
      return

    loanApplication: (loanObj) ->
      console.log 'Ready to send loan application ' + JSON.stringify loanObj
      HTTP.post 'http://localhost:8081/applyLoan', loanObj, (err, result) ->
        if err
          console.error err
        console.log 'Response received from BPM app'
        return
      return