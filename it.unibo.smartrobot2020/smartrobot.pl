%====================================================================================
% smartrobot description   
%====================================================================================
context(ctxsmartrobotcaller, "localhost",  "TCP", "8023").
context(ctxsmartrobot, "192.168.1.4",  "TCP", "8020").
 qactor( smartrobot, ctxsmartrobot, "external").
  qactor( smartrobotcaller, ctxsmartrobotcaller, "it.unibo.smartrobotcaller.Smartrobotcaller").
