@Grab('com.xlson.groovycsv:groovycsv:1.3')
import static com.xlson.groovycsv.CsvParser.parseCsv

def createDeploymentJob(jobName, repoUrl) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches('master')
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("Jenkinsfile")
            }
        }
    }
}

def createTestJob(jobName, repoUrl) {
    multibranchPipelineJob(jobName) {
        branchSources {
            git {
	        id('123456789')
                remote(repoUrl)
                includes('*')
            }
        }
        triggers {
            cron("H/5 * * * *")
        }
    }
}

def buildPipelineJobs() {
    def repo = "https://github.com/AsadUkh/Seed-Another/"
    def repoUrl = repo + jobName + ".git"
    def deployName = jobName + "_deploy"
    def testName = jobName + "_test"
	def Wks = "%WORKSPACE%"

    fh = new File('$Wks/dsl/repos.csv')
	def csv_content = fh.getText('utf-8')
	def data_iterator = parseCsv(csv_content, readFirstLine: true)
//	def repo = []
	def job = []
	for (line in data_iterator) {
	createDeploymentJob(line[0], line[1])
   // repo.add(line[1]) as String
  //  job.add(line[0]) as String
  
	}
    createDeploymentJob(deployName, repoUrl)
    createTestJob(testName, repoUrl)
}

buildPipelineJobs()
