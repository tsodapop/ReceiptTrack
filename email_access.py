# usr/local/bin/python3.7

from __future__ import print_function
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request

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

##########################################################################################
##########################################################################################
##########################################################################################

def main():
#CREDENTIALS----------------------------------------------------------------------------------------------------------------------------- # 
    creds = get_credentials()
#CONNECTION----------------------------------------------------------------------------------------------------------------------------- # 
    #the connection to the google api. we use this to get the results
    service = build('gmail', 'v1', credentials=creds)
#GET INBOX MESSAGES--------------------------------------------------------------------------------------------------------------------- # 

    inbox_messages = get_inbox_messages(service,'me') #all the messages in inbox
    print("number of inbox messages:",len(inbox_messages))

    message_list = []
    # for inbox_message in inbox_messages:
    #     message_id = inbox_message['id'] #get the id of the message of interest
    #     message = service.users().messages().get(userId='me', id=message_id).execute() #get the message
    #     message_list.append(message)

    message_id = inbox_messages[0]['id'] #get the id of the message of interest
    message = service.users().messages().get(userId='me', id=message_id, format="full").execute() #get the message
    message_list.append(message)    

    # print(message)
    # print(message_list[0])
    for i in message_list:
        print(i['payload']['headers']['name' == "Subject"]['value'])
        # print(i['payload']['headers']['name'])

        for j in i['payload']['headers']:
            # if j['name'] == 'Subject':
            print("\n", j['value'])

        

    #we've gotten to the actual list of emails, now we just need to get the email headers and contents



if __name__ == '__main__':
    main()