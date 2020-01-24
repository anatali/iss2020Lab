import serial
import  time

ser = serial.Serial('/dev/ttyUSB0', 115200) 
if ser.isOpen():
    try:
        time.sleep(2)  		#give the Arduino time to reboot
        #write data
        print("write data ")
        ser.write( "z".encode()  ) #"z\r\n".encode()
        time.sleep(1)  #give the serial port sometime to receive the data
        ser.flushOutput()
 
        numOfLines = 0
        print("read data ")
        while True:
            response = ser.readline().decode()
            print("read data: " + response)
            numOfLines = numOfLines + 1
            if (numOfLines >= 5):
              break
        ser.close()
    except:
        print( "error communicating...  "  )

else:
    print( "cannot open serial port " )

def moveRobot( move ):
    ser.write( move.encode()  )
     
def console() :  
    print("console  STARTS :"   )
    cmd =  str( input() )
    #print("console  cmd= :" , cmd  )   # w,s,a,d,r,l,z,x
    while( cmd != "q"  ) :
        #print( cmd )
        moveRobot( cmd )
        #read() #blocking ...
        cmd = str(input() )

console()    