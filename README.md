# drug-drug-interaction
# Disclaimer
A search engine for Drug-Drug Interaction. Although the results are based on FDA databases it was not optimized and were not reviewed by any medical professional so do not take any advice from the results.

# Prequist for Window:
- window system must have docker or docker desktop install, follow below the link for more instructions
- https://www.docker.com/products/docker-desktop/

# Application setup:
- This is the setup for vs code IDE
  1. clone the project to vscode
  2. install the dev container extension
  3. press Crt + shift p and search Dev Containesr: Rebuild Container
  4. Create OpenAI API key, open a terminal in vscode and run export openai.api.key=your api key, then test if it is store correct by running echo $OPENAI_API_KEY -> this echo your api key back 
  5. navigate to backend-services and run (./run.sh enter ) the shell script to start the backend-services if you can't run it, run chmod +x run.sh and run again
  6. .. enter and navigate to ddi-ui and run npm install and npm run dev to start UI, follow the link to open http://localhost:5173 on default
  7.  start using the application.
      
