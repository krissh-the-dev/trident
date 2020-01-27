import sys
import os
import json
from preprocessed_files  import HTML_STRING,readme,extension_html,popupfile,linearRegression,basic_plot
import subprocess
import gitignore
    
#Directory Selection         
def select_dir():
    while(True):
 
        DIR = list(os.listdir('./'))
        print(" > Select the directoy you want  to  create your Project \n> Press '*' to create a new directory \n> To create in Current Directory Press '#'")
        for index, dirs in enumerate(DIR):
            print("{0}) {1}".format(index, dirs))
        ch = str(input("Enter your choice: "))
        if ch == '*':
            directory_name = input("Enter the name of the Directory You want to create: ")
            os.mkdir(directory_name)
            return directory_name
            break
        elif ch == '#':
            return os.getcwd()    
        elif int(ch) <= len(DIR) -1 :
            selected_dir = DIR[ int(ch)]
            return select_dir
            break
        elif int(ch) > len(DIR - 1):
            print("Enter a valid Choice \n")
            continue
            
# BoilerPlate Code for Django             
def create_django(project_name=None, dir_to_create=None,app_name=None):
    if(dir_to_create == None):
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
        if(project_name ==None):
            print(" 1) Enter the Project Name: ")
            
            project_name = input()
            project_name.replace(' ', '_')
            if (project_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue

        project_name = project_name.strip().lower()
        if(app_name ==None):
            print(colors.yellow,"2) Enter the App Name: ")
            app_name=input()
            app_name.replace(' ', '_')
            if (app_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue
            
        app_name = app_name.strip().lower()
        try:
            print("Please Wait Creating Your Awesome Django Project... ")
            
            DEVNULL= open(os.devnull,'wb')
            p = subprocess.Popen(['django-admin startproject ' + project_name ], stdout=DEVNULL, stderr=subprocess.STDOUT,shell=True)
            output=p.communicate()
            os.chdir(project_name)
            with open("README.md", 'a') as f:
                f.write(readme.format(project_name, project_name, "Django"))
            with open('.gitignore', 'a') as f:
                f.write(gitignore.DJANGO_GIT_IGNORE)
                  
            pr = subprocess.Popen(['django-admin startapp ' + app_name], stdout=DEVNULL, stderr=subprocess.STDOUT,shell=True)
            out = pr.communicate()[0]
            if pr.returncode == 0:
                print("Project Created Succesfully")
                sys.exit()
                DEVNULL.close()
        except subprocess.CalledProcessError as e:
               if p.returncode != 0:
                   print("It seems you have not Installed Django in Your System do you want  to install and continue[Yes/No]")
                   choice = input()
                   if (not choice.lower() == 'yes'):
                       sys.exit()
                   if (choice.lower() == 'yes'):
                       print("Installing Django ..")
                       
                       p=subprocess.Popen(['pip3 install django'], stdout=DEVNULL, shell=True, stderr=subprocess.STDOUT)
                       output = p.communicate()
                       
                       if p.returncode == 0:
                           print("Django installed Succesfully rerun command to create project")
                           break
                       else:
                            print(colors.cyan,"Couldn't install Django please Try again")
                            

                            sys.exit()
                  
#Boiler Plate Code for Flutter                                  
def create_flutter(project_name=None, dir_to_create=None):
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
       if(project_name ==None):
            print("Enter Project name: ")
            project_name = input()
            if project_name.isspace():
                print( "Please Enter a valid Project Name to continue")
                continue
       try:
            print("Please Wait Creating Your Awesome Flutter  Project ..")
            
            DEVNULL= open(os.devnull,'wb')
            p = subprocess.Popen(['flutter create ' + project_name ], stdout=DEVNULL, stderr=subprocess.STDOUT,shell=True)
            output = p.communicate()
            if p.returncode == 0:
                print("Project Created Succesfully !!! ")
                sys.exit()

       except subprocess.CalledProcessError  as e:
           if p.returncode != 0:
               print("Error ! Couldn't find Flutter in your system please follow this and install and continue   \nhttps://flutter.dev/docs/get-started/install")
                   
               sys.exit()
# BoilerPlate Code for React               
def create_react(project_name=None, dir_to_create=None):
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
        if project_name == None :
            print("Enter Project name: ")
            
            project_name = input()
            project_name.replace(' ', '_')
            if (project_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue
        try:
                print("Please Wait. We're creating your Awesome React Project!! ")
                DEVNULL = open(os.devnull, 'wb')
               
                p = subprocess.Popen(['npx create-react-app '  + project_name ], stdout=DEVNULL, stderr=subprocess.STDOUT,shell=True)
                output = p.communicate()[0]
                if p.returncode == 0:
                    print("Project Created Succesfully !!! ")
                    
                    sys.exit()

        except subprocess.CalledProcessError  as e:
            if p.returncode != 0:
               print("It seems that 'Node' is not installed in your System. Please follow the below link to install \nhttps://nodejs.org/en/download/ ")
               sys.exit()
            
#Boiler Plate Code for Vue
def create_vue(project_name=None, dir_to_create=None):
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
        if project_name ==None:
            print("Enter Project name: ")
            
            project_name = input()
            project_name.replace(' ', '_')
            if (project_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue
        try:
                print("Please Wait Creating Your Awesome Vue Project !! ")
                DEVNULL = open(os.devnull, 'wb')
               
                p = subprocess.Popen(['vue create -b '  + project_name ], stdout=DEVNULL ,stderr=subprocess.STDOUT,shell=True,stdin=subprocess.PIPE)
                output = p.communicate(input = b'\n')[0]
                if p.returncode == 0:
                    print("Project Created Succesfully !!! ")
                    
                    sys.exit()

        except subprocess.CalledProcessError  as e:
            if p.returncode != 0:
                if p.returncode != 0:
                   print("It seems you have not Installed Vue  in Your System do you want  to install and continue[Yes/No]")
                   choice = input()
                   if (not choice.lower() == 'yes'):
                       sys.exit()
                   if (choice.lower() == 'yes'):
                       print("Installing Vue ..")
                       
                       p=subprocess.Popen(['npm install -g @vue/cli '], stdout=DEVNULL, shell=True, stderr=subprocess.STDOUT)
                       output = p.communicate()
                       
                       if p.returncode == 0:
                           continue
                       else:
                            print("Couldn't install Vue please try again, Install it manually or make sure npm is installed, Follow the docs to install npm \n https://nodejs.org/en/download/ ")
                            
                            sys.exit()
#BoilerPlate Code for HTML            
def create_html(project_name=None, dir_to_create=None):
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
        if project_name == None:
            print("Enter Project name: ")
            
            project_name = input()
            project_name.replace(' ', '_')
            if (project_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue
        else:
            print("Creating Your Awesome HTML Project")
            
            try:
                os.makedirs(project_name)
                os.chdir(project_name)
                with open("README.md", 'a') as f:
                    f.write(readme.format(project_name, project_name, "HTML"))

                with open('index.html', 'w') as file:
                    file.write(HTML_STRING.format(project_name.title(),project_name.title()))
                print("Project Created Sucessfully")
                sys.exit()
            except FileExistsError as e:
                print(project_name  + " already exists please mention a different name ")
                break
                
#BoilerPlate Code for Chrome Extension        
def create_extension(project_name=None, dir_to_create=None):
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(dir_to_create)
    while (True):
        if project_name ==None:
            print("Enter Project name: ")
            
            project_name = input()
            project_name.replace(' ', '_')
            if (project_name == ''):
                print("Please Enter a valid Project Name to continue")
                continue
        else:
            print("Creating Your Awesome HTML Project")
            
            try:
                os.makedirs(project_name)
                os.chdir(project_name)
                with open("README.md", 'a') as f:
                    f.write(readme.format(project_name, project_name, "Chrome Extension"))

                with open('index.html', 'w') as f:
                    f.write(extension_html.format(project_name.title()))
                with open('popup.js') as f:
                    f.write(popupfile)
                       
                print("Project Created Sucessfully")
                sys.exit()
            except FileExistsError as e:
                print(project_name  + " already exists please mention a different name ")
 
                break
#Boiler Plate Code for ML                
def create_ml(project_name=None, dir_to_create=None):
    DEVNULL = open(os.devnull, 'wb')
    if dir_to_create == None:
        dir_to_create = select_dir()
        os.chdir(select_dir)
        print("Is the PC setup with all dependencies [Yes/No] ? \n")
        choice = input()
        if choice.lower() == 'yes' or choice.lower() == 'y':
            print("The following packages will be installed 1) Sci- Kit Learn \n 2) Numpy  \n 3) Matplotlib \n 4)Pandas \n [Yes/No]")
            option = input()
            if option.lower == 'yes' or option.lower() == 'y':
               project_dir= os.mkdir("BoilerML")
               os.chdir(project_dir)
               print("Installing Numpy ... ")
               p = subprocess.Popen(['pip3 install numpy'], stdout=DEVNULL, stderr=subprocess.STDOUT, shell=True)
               output = p.communicate()[0]
               if p.returncode == 0:
                   print("Installed Numpy")
               else:
                   print("Unexpected Error occured, Please try again later")
                   sys.exit()

               p = subprocess.Popen(['pip3 install matplotlib'], stdout=DEVNULL, stderr=subprocess.STDOUT, shell=True)
               output = p.communicate()[0]
               if p.returncode == 0:
                   print("Installed Matplotlib")
               else:
                   print("Unexpected Error occure")
                   sys.exit()
                   p = subprocess.Popen(['pip3 install sklearn'], stdout=DEVNULL, stderr=subprocess.STDOUT, shell=True)
                   output = p.communicate()[0]
                   if p.returncode == 0:
                       print("Installed sucessfully al the Pakcages")
                       print("Powerboiling Please wait ....")
                       with open("README.md ", 'a') as f:
                            f.write(readme.format(project_name, project_name, "ML Project"))
                       with open("basic_model.py", 'a') as f:
                             f.write(linearRegression)
                       with open("basic_plot.py") as f:
                            f.write(basic_plot)
def main():
    if len(sys.argv) == 1:
        FRAMEWORKS=['Django','Vue','Flutter','React' ,'HTML','Chrome Extension']
        print("============ PowerBoil ========== \nBoiler Plate codes for the following frameworks")
        for index, frameworks in enumerate(FRAMEWORKS):
            print("{}) {}".format(index, frameworks))

     
        choice = str(input("Enter your Choice: "))

        if (choice == '0'):
            create_django()
        elif(choice == '1'):
            create_vue()
        elif (choice == '2'):
            create_flutter()
        elif (choice == '3'):
            create_react()
        elif (choice == '4'):
            create_html()
        elif (choice == '5'):
            create_extension()    
        else:
            print("Invalid Choice")
            sys.exit()                         
    if (sys.argv[1] == 'django'):
        create_django(project_name=sys.argv[2],dir_to_create=os.getcwd())
    elif sys.argv[1] == 'react':
        create_react(project_name=sys.argv[2],dir_to_create=os.getcwd())
    elif sys.argv[1] == 'flutter':
        create_flutter(project_name=sys.argv[2], dir_to_create=os.getcwd())
    elif sys.argv[1] == 'vue':
        create_vue(project_name=sys.argv[2], dir_to_create=os.getcwd())
    elif sys.argv[1] == 'html':
         create_html(project_name=sys.argv[2], dir_to_create=os.getcwd())
    elif sys.argv[1] == 'extension':
        create_extension(project_name=sys.argv[2],dir_to_create=os.getcwd())     
if __name__ == "__main__":
   main()
