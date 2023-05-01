# Kotlin API Template
A template rest API written in Kotlin following Clean Architecture. This repo is meant to be forked so that a "real"
service can be built.

This repo was initialized with Spring Initializr

## Setup
### Name Your Service
First, rename the repo and replace all occurrances of `kotlin-api-template` with your real service's name. This must be done it at least:
* `settings.gradle.kts`
* `.github/workflows/build-and-test.yml`
* All files in `script/aws`
* `docker-compose.yml`
* `src/main/resources/application.yml`

### Install Dependencies
Assure the following are installed:
* Docker
* Java 17
* AWS CLI

## Development
To keep behavior as consistent with deployed enviornments as possible, development using a local containerized environment is recommended. To stand up a local environment, run the following:

```sh
> ./script/run-local
```

This will create a Postgres database and start the service. To confirm that the application started up correctly:

```sh
> curl localhost:8080/api/health
{"healthy":true}
```

## Deploy
Initial deployment must be done somewhat manually. This can be done with the scripts included in `script/aws/`. Before running these scripts, use the AWS CLI to authenticate as a user with access to create the required resources. A policy like this should work, but likely grants a bit too much access:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ecs:ListServicesByNamespace",
                "ecs:DiscoverPollEndpoint",
                "ecs:PutAccountSettingDefault",
                "ecs:CreateCluster",
                "cloudformation:*",
                "ecs:DescribeTaskDefinition",
                "ecs:PutAccountSetting",
                "ecs:ListServices",
                "ecs:CreateCapacityProvider",
                "ecs:DeregisterTaskDefinition",
                "ecs:ListAccountSettings",
                "ecs:DeleteAccountSetting",
                "ecs:ListTaskDefinitionFamilies",
                "ecs:RegisterTaskDefinition",
                "ecs:ListTaskDefinitions",
                "ecs:CreateTaskSet",
                "ecs:ListClusters"
            ],
            "Resource": "*"
        },
        {
            "Effect": "Allow",
            "Action": [
                "iam:PassRole",
                "secretsmanager:GetSecretValue"
            ],
            "Resource": "*"
        },
        {
            "Effect": "Allow",
            "Action": "ecs:*",
            "Resource": "*"
        }
    ]
}
```

Once logged in with sufficient priviliges, run scripts in the following order:
```sh
./script/aws/create/create-network-stack
./script/aws/create/create-database-stack
./script/aws/create/create-service-stack
```

After initial creation, resources can be modified with the scripts included in `script/aws/update`.

### CI/CD
Any pushes to the `master` branch will result in automated deployments to AWS. For these to work, Githb Actions needs a way to access the target AWS account. This can be accomplished by creating a user with access credentials and adding them to the repo's repository secrets. This user's access should be similar to what is listed above for resource creation, but allowed resources can be more specifically specified.

## API Documentation
When running locally, Swagger API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

