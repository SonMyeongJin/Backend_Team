#!/bin/bash

touch /home/ubuntu/deploy.log

/home/ubuntu/deploy.sh > /home/ubuntu/deploy.log 2> /home/ubuntu/deploy.log < /dev/null &
