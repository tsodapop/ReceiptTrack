# usr/local/bin/python3.7

from __future__ import print_function
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import pandas as pd
import os

# If modifying these scopes, delete the file token.pickle.
SCOPES = ['https://www.googleapis.com/auth/gmail.readonly']
# ----------------------------------------------------------------------------------------------------------------------------- #    
#get the messages from inbox, whether read or unread
def get_inbox_messages(service, userId):
    # we grab INBOX label from the gmail API to check 
    results = service.users().labels().list(userId='me').execute()
    labels = results.get('labels', [])

    inbox_labels = ['INBOX']

    # get all the messages 
    messages_list = service.users().messages().list(userId='me', labelIds=inbox_labels).execute()
    messages_list = messages_list['messages']
    return messages_list


# ----------------------------------------------------------------------------------------------------------------------------- #
def get_credentials():
    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first time
    if os.path.exists('token.pickle'):
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token)
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'credentials.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)  
    return creds   

# ----------------------------------------------------------------------------------------------------------------------------- #
def getSubject(headers):
    for header in headers:
        if header['name'] == 'Subject':
            return header['value']
        else:
            continue

##########################################################################################
##########################################################################################
##########################################################################################

def main():

#clear out the terminal for cleanliness
    os.system('clear')


#CREDENTIALS----------------------------------------------------------------------------------------------------------------------------- # 
    creds = get_credentials()
#CONNECTION----------------------------------------------------------------------------------------------------------------------------- # 
    #the connection to the google api. we use this to get the results
    service = build('gmail', 'v1', credentials=creds)
#GET INBOX MESSAGES--------------------------------------------------------------------------------------------------------------------- # 

    inbox_messages = get_inbox_messages(service,'me') #all the messages in inbox
    print(f"number of inbox messages: {len(inbox_messages)}")

    message_list = []
    for inbox_message in inbox_messages:
        message_id = inbox_message['id'] #get the id of the message of interest
        message = service.users().messages().get(userId='me', id=message_id).execute() #get the message
        message_list.append(message)

    #TESTING ON INDIVIDUAL EMAIL, ABOVE IS ALL EMAILS
    # message_id = inbox_messages[0]['id'] #get the id of the message of interest
    # message = service.users().messages().get(userId='me', id=message_id, format="full").execute() #get the message
    # message_list.append(message)    

    
    subjects = [] #store the email titles

    for i in message_list: #for every message
        headers = i['payload']['headers']

        subject = getSubject(headers) #function that will return the subject

        # for names in headers: #loop through all headers
            # if names['name'] == 'Subject': #if they are the subject title, get the title
                # title = names['value']
                
        subjects.append(subject) #add title to the subjects list
    
#SAVE SUBJECTS AS CSV TO MANUALLY CLASSIFY------------------------------------------------------------------------------------------------- #  
    subjects_df = pd.DataFrame(columns = ['Subject','Receipt'])
    subjects_df['Subject'] = subjects #put the subjects into the subjects column
    subjects_df['Receipt'] = 0 #default the value to 0
    subjects_df.to_csv(r'email_subjects.csv', index=False) #save the df as a csv

    print("Done")
    #now, we need to manually classify the emails


    
        

    #we have email titles, need to classify on them now



if __name__ == '__main__':
    main()