### Schedule message as following:

`curl -X POST -H'Content-Type: application/json' -H'Accept: application/json' http://localhost:8080/schedule -d'{"captain":"Careem bhai","userFirebaseKey":"some fire base key"}'`

The above will send a message to SQS (just log, write implementation) and schedule it after 20 seconds, see com.munchies.ping.scheduler.SchedulerService#RESCHEDULE_SECONDS