%====================================================================================
% smartrobot description   
%====================================================================================
<<<<<<< HEAD
context(ctxsmartrobot, "localhost",  "TCP", "8020").
context(ctxbasicrobot, "192.168.1.6",  "TCP", "8018").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( smartrobot, ctxsmartrobot, "it.unibo.smartrobot.Smartrobot").
  qactor( smartperceiver, ctxsmartrobot, "it.unibo.smartperceiver.Smartperceiver").
=======
context(ctxsmartrobotcaller, "localhost",  "TCP", "8023").
context(ctxsmartrobot, "192.168.1.4",  "TCP", "8020").
 qactor( smartrobot, ctxsmartrobot, "external").
  qactor( smartrobotcaller, ctxsmartrobotcaller, "it.unibo.smartrobotcaller.Smartrobotcaller").
>>>>>>> 34d7120980a947fea5bf2bab6dd2d5aaa1b24dd9
