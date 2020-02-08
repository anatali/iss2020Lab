#!/bin/sh
echo "basicrobot starting ..."
cd mjpg-streamer/mjpg-streamer-experimental
sleep 0.3
echo "start mjpg"
export LD_LIBRARY_PATH=.
./mjpg_streamer -i "input_uvc.so" -o "output_http.so -p 8090 -w /usr/local/share/mjpg-streamer/www" &
## sleep 0.3
## cd ..
## cd ..
sleep 0.3
## cd it.unibo.basicrobot2020-1.0/bin
## bash  it.unibo.basicrobot2020




