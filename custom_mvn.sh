#!/bin/sh
sed -i "s/<\/settings>/<mirrors><mirror><id>insecure-repo<\/id><mirrorOf>p2Repo<\/mirrorOf><url>http:\/\/updates.gama-platform.org\/1.8.2<\/url><blocked>false<\/blocked><\/mirror><mirror><id>insecure-repoip<\/id><mirrorOf>p2Repo<\/mirrorOf><url>http:\/\/$SSH_HOST<\/url><blocked>false<\/blocked><\/mirror><mirror><id>insecure-repoexp<\/id><mirrorOf>p2Repo<\/mirrorOf><url>http:\/\/updates.gama-platform.org\/experimental\/1.8.2<\/url><blocked>false<\/blocked><\/mirror><mirror><id>insecure-buchen-maven-repo<\/id><mirrorOf>buchen-maven-repo<\/mirrorOf><url>http:\/\/buchen.github.io\/maven-repo<\/url><blocked>false<\/blocked><\/mirror><mirror><id>maven-default-http-blocker<\/id><mirrorOf>external:http:*<\/mirrorOf><url>http:\/\/0.0.0.0\/<\/url><blocked>false<\/blocked><\/mirror><\/mirrors><\/settings>/" ~/.m2/settings.xml

# Sign jar - issue #25
sed -i "s/<\/settings>/<profiles><profile><id>gpg</id><properties><gpg.passphrase>${secrets.GPG_PASSPHRASE}<\/gpg.passphrase><gpg.keyname>${secrets.GPG_KEYNAME}<\/gpg.keyname><\/properties><\/profile><\/profiles><\/settings>/" ~/.m2/settings.xml


    