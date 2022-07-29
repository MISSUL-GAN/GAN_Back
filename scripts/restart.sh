
PROJECT_NAME=missulgan
DIRECTORY=/home/ubuntu/app

echo "PID Check..."
CURRENT_PID=$(ps -ef | grep java | grep Server | awk '{print $2}')

echo "Running PID: {$CURRENT_PID}"

if [ -z ${CURRENT_PID} ] ; then
        echo "Project is not running"
else
        echo "Kill Current PID"
        kill -9 $CURRENT_PID
        sleep 10
fi

echo "Running..."

nohup java -jar $DIRECTORY/deploy/$PROJECT_NAME-0.0.1-SNAPSHOT.jar >> $DIRECTORY/server.log &

echo "Done"