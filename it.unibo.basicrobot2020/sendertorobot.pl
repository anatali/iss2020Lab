%====================================================================================
% sendertorobot  description    (written by the aplication designer)
%====================================================================================
 
context(ctxsenderrobot, "localhost",  "TCP", "8038").
  qactor( sendertorobot, ctxsenderrobot, "itunibo.robot.cmdsender.cmdsenderActor").
 