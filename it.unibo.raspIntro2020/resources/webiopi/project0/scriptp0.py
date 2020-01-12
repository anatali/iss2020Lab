import webiopi
import datetime

GPIO = webiopi.GPIO

LIGHT = 17 # GPIO pin using BCM numbering

 
# setup function is automatically called at WebIOPi startup
def setup():
    # set the GPIO used by the light to output
    GPIO.setFunction(LIGHT, GPIO.OUT)
    # retrieve current datetime
    now = datetime.datetime.now()
    print("			scriptLed17Blink | SETUP at:" + str(now) )       
    state = True
    for x in range(0, 6):   
	    GPIO.digitalWrite(LIGHT, state)
	    state = not state
	    # gives CPU some time before looping again
	    webiopi.sleep(0.5)

# Looped by WebIOPi
def loop():
    # Toggle LED each 5 seconds
    webiopi.sleep(5)        
    value = not GPIO.digitalRead(LIGHT)
    GPIO.digitalWrite(LIGHT, value)

# A macro which says hello
@webiopi.macro
def HelloWorld(first, last):
    webiopi.debug("HelloWorld(%s, %s)" % (first, last))
    return "Hello %s %s !!!!" % (first, last)

# A macro without args which return nothing
@webiopi.macro
def PrintTime():
    webiopi.debug("PrintTime: " + time.asctime())


# destroy function is called at WebIOPi shutdown
def destroy():
    GPIO.digitalWrite(LIGHT, GPIO.LOW)

