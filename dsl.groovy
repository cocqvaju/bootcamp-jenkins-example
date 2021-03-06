//def gitRemoteUrl = 'https://github.com/jeroenschepens/bootcamp-jenkins-example'
def gitRemoteUrl = 'https://github.com/tsteenbakkers/bootcamp-jenkins-example'

// Step 1
out.println('Setting up the Checkout job (Step 1)')
freeStyleJob('Step 1 - Checkout Job') {
	description("Checks out a branch from the repository on Github. See ${gitRemoteUrl}#step-1---create-checkout-job \n-- Generated by the DSL job.")
	
	scm {
		git {
			remote {
				url("${gitRemoteUrl}.git") // Double quotes, so the value gets inserted

			}
			branch('*/master') // Single quotes, needs to be literal
		}
	}
}
// End of Step 1

// Step 2
out.println('Setting up the Compile job (Step 2)')
freeStyleJob('Step 2 - Compile Job') {
	description("Checks out a branch from the repository on Github and compiles the code using Maven. See ${gitRemoteUrl}#step-2---create-compile-job \n-- Generated by the DSL job.")
	
	scm {
		git {
			remote {
				url("${gitRemoteUrl}.git") // Double quotes, so the value gets inserted

			}
			branch('*/step2') // Single quotes, needs to be literal
		}
	}

	steps {
        maven {
        	goals('clean compile')
            mavenInstallation('M3')
        }
    }
}
// End of Step 2

// Step 3
out.println('Setting up the Unit Test job (Step 3)')
freeStyleJob('Step 3 - UnitTest Job') {
	description("Checks out a branch from the repository on Github and performs unit tests using Maven. See ${gitRemoteUrl}#step-3---create-unit-test-job \n-- Generated by the DSL job.")
	
	scm {
		git {
			remote {
				url("${gitRemoteUrl}.git") // Double quotes, so the value gets inserted

			}
			branch('*/step3') // Single quotes, needs to be literal
		}
	}

	steps {
        maven {
        	goals('clean test')
            mavenInstallation('M3')
        }
    }
}
// End of Step 3

// Step 4
out.println('Setting up the Notification job (Step 4)')
freeStyleJob('Step 4 - Notification Job') {
	description("Checks out a branch from the repository on Github and performs unit tests using Maven. \n-- Informs someone by e-mail based on the outcome of the mail. \n-- This also gives an example of using parameters. In this case it\'s perhaps somewhat an overkill. Check https://www.mailinator.com/inbox2.jsp?public_to=bootcamp2017-jenkins if the mail has been received! \n-- See ${gitRemoteUrl}#step-4---create-deployment-job \n-- Generated by the DSL job. \n-- Check https://www.mailinator.com/inbox2.jsp?public_to=bootcamp2017-jenkins")
	
	parameters {
		stringParam("EMAIL_ADDRESS", "bootcamp2017-jenkins@mailinator.com", "The e-mail address to receive the mail")
	}

	scm {
		git {
			remote {
				url("${gitRemoteUrl}.git") // Double quotes, so the value gets inserted

			}
			branch('*/step3') // Single quotes, needs to be literal
		}
	}

	steps {
        maven {
        	goals('clean compile package')
            mavenInstallation('M3')
        }
    }

    publishers {
        archiveArtifacts('target/*.jar')
        mailer('${EMAIL_ADDRESS}', true, true) // Single quotes, we want to use the actual parameter instead of the value
    }
}
// End of Step 4

// Step 5
out.println('Setting up the Pipeline job (Step 5)')
pipelineJob('Step 5 - Pipeline Job') {
	description("Executes step 1 till 4 in a pipeline. See ${gitRemoteUrl}#step-5---create-pipeline-with-all-jobs \n-- Generated by the DSL job.")
	definition {
		cps {
			script('''node {
   stage("Checkout") {
   		echo "Starting checkout job"
      	build job: "Step 1 - Checkout Job"
   }

   stage("Compile") {
   		echo "Starting compile job"
      	build job: "Step 2 - Compile Job"
   }

   stage("UnitTest") {
   		echo "Starting unit test job"
      	build job: "Step 3 - UnitTest Job"
   }

   stage("Notify") {
   		echo "Starting notify job"
      	build job: "Step 4 - Notification Job", parameters: [
              string(name: 'EMAIL_ADDRESS', value: "m8r-xgtl74@mailinator.com")
          ]
   }
}
''')
		}
	}
}
// End of Step 5