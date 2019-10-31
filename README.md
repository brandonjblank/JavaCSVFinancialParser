# JavaCSVFinancialParser
A program that checks the vendor descriptions of CSV transaction files for non-vendor detaails, and parses the information. Data is displayed in a cleaner format, making it useful for aggregating venbdor data in models that utilize financial statements.

Examples of data removed include "#123", "_V", and "00.00_V"

## What formats does it support?
Currently, JavaCSVFinancialParser supports csv files that use the following format:

Date | Transaction Description | Withdrawals | Deposits | Balance

An example of a banking institution that uses this format is Toronto-Dominion (TD) Bank.

## What does it not do?
Currently JavaCSVFinancialParser only supports a hard format and does not allow for more flexible options. This will be changed in a future update. The program also does not consider the existence of header rows in CSV files. 

## How does it work?
Startup the program, and select a csv file. Next, wait till the success or failure prompt on screen. On success, simply check the same folder as your original file and look for a gibberish csv file. Open it up, and your transaction descriptions should now be cleaner. When a prompt displays failure, it means that the CSV file could not be properly parsed.

## What is it built off of?
This program uses a mix of standard Java and JavaFX libraries. It is still a work-in-progress, so the final version may incorporate additional languages.

##Can I offer suggestions?
Sure! Start by creating an issue, and we can go from there.
