#!/bin/bash

# This script allows us to build and push the Docker image of a service...
# (For using it, your should be in Linux.)

regular_green='\033[0;32m';
bold_yellow='\033[1;33m';
bold_blue='\033[1;34m';
bold_magenta='\033[1;35m';
bold_red_background='\033[1;41m';

neutral='\033[0;m';

service_name=$1;

exit_command()
{
	command=$?;
	if [ $command -ne 0 ]; then
		exit $command;
	fi
}

dockerize()
{
	# For logging in to our Docker account "scp1920"...
	echo -e "\n\n${bold_magenta}LOGGING IN to our Docker account \"scp1920\"...${neutral}";
	docker login -u scp1920 -p laborious;

	cd dronizone-$service_name;

	chmod +x mvnw
	# For creating an executable JAR for the service...
	echo -e "\n\n${bold_magenta}CREATING an executable JAR for the service...${neutral}";
	./mvnw clean package -DskipTests; exit_command;

	# For testing this JAR...
	echo -e "\n\n${bold_magenta}TESTING this JAR...${neutral}";
	java -jar target/dronizone-$service_name-0.0.1-SNAPSHOT.jar &

	sleep 10;

	# For building the Docker image (of the service) from this JAR...
	echo -e "\n\n${bold_magenta}BUILDING the Docker image (of the service) from this JAR...${neutral}";
	./mvnw dockerfile:build; exit_command;

	# For pushing this Docker image to our Docker repository "dronizone"...
	echo -e "\n\n${bold_magenta}PUSHING this Docker image to our Docker repository \"dronizone\"...${neutral}";
	./mvnw dockerfile:push;
}

if [[ -n $* ]]; then
	dockerize;
	echo -e "\n\n${bold_yellow}For testing the image, start to kill the JAR process:${neutral}";
	echo -e "    netstat -pna | grep ${bold_blue}<${regular_green}service_port${bold_blue}>${neutral}";
	echo -e "    kill -9 ${bold_blue}<${regular_green}service_process_id${bold_blue}>${neutral}";
	echo -e "    ${bold_red_background}(WARNING! Be careful, because it can be risky operation.)${neutral}";
	echo -e "\n${bold_yellow}Then launch the following commands:${neutral}";
	echo "    docker pull scp1920/dronizone:$service_name";
	echo "    docker run -t scp1920/dronizone:$service_name";
	echo -e "${bold_yellow}or, for testing in the background:${neutral}";
	echo -e "    docker run -d -p ${bold_blue}<${regular_green}service_port${bold_blue}>${neutral}:${bold_blue}<${regular_green}service_port${bold_blue}>${neutral} -t scp1920/dronizone:$service_name";
	echo -e "\n${bold_yellow}For removing the container, launch the two following commands:${neutral}";
	echo "    docker container ls";
	echo -e "    docker rm -f ${bold_blue}<${regular_green}container_id${bold_blue}>${neutral}";

else
	echo "Enter the name of the service that you have to dockerize. (For example, enter \"warehouse\" for the \"Warehouse Service\".)";
fi
