EDITING_KEY = "editingList"
Session.setDefault EDITING_KEY, false

###
  Initial settings
###
Session.setDefault("currentuser", null)
Session.setDefault("Orders", "")
Session.setDefault "errormessage", null

# Track if this is the first time the list template is rendered
firstRender = true
listRenderHold = LaunchScreen.hold()
listFadeInHold = null
Template.appMain.rendered = ->
  if firstRender
    # Released in app-body.js
    listFadeInHold = LaunchScreen.hold()
    
    # Handle for launch screen defined in app-body.js
    listRenderHold.release()
    firstRender = false
  @find(".js-title-nav")._uihooks =
    insertElement: (node, next) ->
      $(node).hide().insertBefore(next).fadeIn()

    removeElement: (node) ->
      $(node).fadeOut ->
        @remove()

Template.appMain.helpers
  errormessage: ->
    Session.get("errormessage")

  editing: ->
    Session.get EDITING_KEY

Template.appMain.events
  "click button":->
    Session.set("errormessage", "")

  "click .js-cancel": ->
    Session.set EDITING_KEY, false
    return

  "keydown input[type=text]": (event) ->
    
    # ESC
    if 27 is event.which
      event.preventDefault()
      $(event.target).blur()
    return

  "blur input[type=text]": (event, template) ->
    
    # if we are still editing (we haven't just clicked the cancel button)
    saveList this, template  if Session.get(EDITING_KEY)
    return