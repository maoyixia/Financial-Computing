{\rtf1\ansi\ansicpg1252\cocoartf1138\cocoasubrtf510
{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset134 STHeitiSC-Light;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural

\f0\fs24 \cf0 README\
\
I use the activeMQ as broker for this project.\
\
First need to add all the required external JARs of this project (in lib directory). I use the jar activemq 5.8.0 here. You can add the specific jar from your ActiveMQ.\
\
Start activeMQ
\f1 .
\f0 \
\
Run Server class to start a server. You can choose the option type (European/Asian) by passing option type name to the constructor of Server class when instantiate.\
\
Run Client class to start a client. You can run multiple clients.\
\
The final option price will display on console when reach convergency. \
\
}