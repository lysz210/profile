# Lysz210 custom data

This project contains all public personal information.

## Data structure

All data are stored in yaml file and should be uploaded into a 
aws.DynamoDB Table with locale as partitionKey and path relative to it as sortKey.
Data must be converted into DynamoDB data type accordingly.