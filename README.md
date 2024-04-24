### This project allows users to use LLM model to create a learning path for themselves. Project contians both frontend and backend code which can be deployed to AWS services. Project contains cloudformation file which could be executed to create a stack and complete the deployment.

### AWS Infrastructure

## Compute

1. AWS EC2 - deploy frontend application
    1. create a simple instance with key pair
    2. add nvm "curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash"
    3. source ~/.bashrc
    4. nvm install --lts
    5. open ec2 instance using WinSCP
    6. transfer dist files to VM
    7. npm install -g serve
    8. nohup serve -l 3000 -s app-name

2. AWS Lambda - deploy backend api
    1. Create a lambda with Java 21 environment and upload jar, change timeout to 1 minute or more

## Storage

1. Dynamo DB - for storing user data
    1. Create a table with id as partition key

## Network

1. AWS API Gateway – Route API requests to lambdas
    1. Create a API gateway with REST type, and create a post endpoint with integration to lambda

## General

1. AWS Secrets manager - used to store database credentials, API key for backend
    1. chatGptApiKey : **************
    2. tableName : cloud-term-assignment
    3. emailFrom : ketulpatel3012@gmail.com
    4. servicesRegion : us-east-1 

2. AWS SES - used to send email
    1. Add two personal email as verified identities, one will be used to send email and one will be used to receive email



### Commands to create and delete stack

Following command will create a stack named learn-pro
```
aws cloudformation create-stack --stack-name learn-pro --template-body=file://cloudformation.yaml --capabilities=CAPABILITY_NAMED_IAM
```

Following command will delete a stack named learn-pro
```
aws cloudformation delete-stack --stack-name learn-pro
```



