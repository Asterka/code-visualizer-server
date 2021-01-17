sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt install curl
sudo apt-get install npm
sudo apt install wget


#Load the compatible nvm and node version 14.13.1
#sudo wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install$
#source ~/.profile

#export NVM_DIR="$HOME/.nvm"
#[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
#[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"
#nvm install 14.13.1

#sudo wget https://github.com/kwart/jd-cli/releases/download/jd-cmd-1.1.0.Final/jd-cli-1.1.0.Final-dist.tar.gz
#rm -r project_files
#mkdir project_files && mkdir project_files/jd-cmd
#tar -xf jd-cli-1.1.0.Final-dist.tar.gz -C project_files/jd-cmd
java -jar ./project_files/jd-cmd/jd-cli.jar ./res/jars/PC-Remote.jar -ods ./classes

########################
#cd res && mkdir deps && mkdir loadedRepo
#cd loadedRepo
#sudo rm -R InnoCrypt && git clone https://github.com/DenisRang/InnoCrypt
#ls

source ~/.bashrc