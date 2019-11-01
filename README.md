# JavaCSVFinancialParser
A program that checks the vendor descriptions of CSV transaction files for non-vendor detaails, and parses the information. Data is displayed in a cleaner format, making it useful for aggregating vendor data in models that utilize financial statements.

Examples of data removed include "#123", "_V", and "00.00_V"

## What formats does it support?
Currently, the main version of JavaCSVFinancialParser supports CSV files that use the following format:

| Date | Transaction Description | Optional Field 1 | Optional Field 2 | ...

The Date field must be contained in the first column, and the second column should contain the Transaction Description. All other fields are considered optional.

The Lite Version currently requires the Transaction Description to be located in the first column, and scales according to the number of columns. The Lite version is useful for pure list functionality and does not require additional columns outside of the mandatory Transaction Description column.

## What does it not do?
The program does not consider the existence of header rows in CSV files. It also does not cover all scenarios when parsing data.

## How does it work?
Startup the program, and select a csv file. Next, wait till the success or failure prompt on screen. On success, simply check the same folder as your original file and look for a gibberish csv file. Open it up, and your transaction descriptions should now be cleaner. When a prompt displays failure, it means that the CSV file could not be properly parsed.

## What is it built off of?
This program uses a mix of standard Java and JavaFX libraries. It is still a work-in-progress, so the final version may incorporate additional languages.

## Can I offer suggestions?
Sure! Start by creating an issue, and we can go from there.
