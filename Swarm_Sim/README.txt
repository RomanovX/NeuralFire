to execute to jar file the folder "environmentMaps" needs to be in the same directory!
the jar now accepts two parameters that specify the interval of runs to execute (both beginning and end are incluse, ie the interval 200 201 will execute both run 200 and 201)
thus, an example execution would be:
"java -jar antstuff.jar 1915 1920"
which would execute runs 1915, 1916, .. , 1920
Each run consits of 8 trials
When you execute the jar, you can see some line at the beginning that specifies total runs, should look like this:
"Now starting 1920 runs with 8 each for a total of 15360 Executions" (this will always be displayed, even if only a subset of runs are executed)
Thus the idea now is to split runs instead of yellingRadius or some other parameter, ie following execution setup:
"java -jar antstuff.jar 1 200" on computer 1
"java -jar antstuff.jar 201 400" on computer 2
....
"java -jar antstuff.jar 1801 1920" on computer X

If an execution was successful, the generated file will start with "COM" otherwise with "INC", also, the run range is included in the file name
thus if you sort the folder alphabetically, you can see if everything finished successfully.

