# JavaCSVFinancialParser
A program that parses CSV statement files for data, removing non-vendor details. Useful for aggregating vendor data in models from financial statements.

## What does it do?
JavaCSVFinancialParser parses csv files that use the following format:

Date | Transaction Description | Withdrawals | Deposits | Balance

It parses out the transaction description field, and removes undesirable content such as #123, 00.00_V, and irregular dashes.

## How does it work?
Startup the program, and select a csv file. Next, wait till the success or failure prompt. On success, simply check the same folder as your original file and look for a gibberish csv file. Open it up, and your transaction descriptions should now be cleaner.
