sudo apt-get update
sudo apt install curl
sudo apt-get install npm
sudo apt install wget
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle 6.7
gradle -v

sudo wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install$
source ~/.profile

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"
nvm install 14.13.1
source ~/.bashrc

########################
cd res && mkdir deps && mkdir loadedRepo
cd loadedRepo
sudo rm -R InnoCrypt && git clone https://github.com/DenisRang/InnoCrypt
ls

