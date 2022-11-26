import requests
import ctypes
import json
import os
import sys
def read_file(path):
    with open(path, 'rb') as f:
        return f.read()

# 200    
def post_json_to_server(post_url, account, data, files=None):
    #header = {"User-Agent": "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36"}
    #r = requests.post(post_url,data=json.dumps(data),auth=(account['name'],account['passwd']),headers = header)
    r = requests.post(post_url, data=data, files=files)
    print(r.content)
    print(r.status_code)

##    station_str = str(files)
##    if 'BFT' in station_str or 'SRF' in station_str:
##        lpr = requests.post(post_url, files=litePointFiles)
##        print(lpr.content)
##        print(lpr.status_code)
##    else:
##        print(r'This is not a RF station, do not need to upload Litepoint log')
    
    # ctypes.windll.user32.MessageBoxW (0, "Your text", "Your title", 1)

# 200    
def get_from_server(get_url, account):
    response = requests.get(get_url,auth=(account['name'],account['passwd']))
    print(response.content)
    #print(response.status_code)


"""def get_path(path):
    if path=="log.json":
        post_url = "http://ambit:bento@10.90.0.15:8100/api/1/eden/test" # Data was valid

    elif path=="log1.json":
        post_url = "http://10.90.0.15:8100/api/1/eden/results"

    return post_url   """

if __name__ == "__main__":

    if(len(sys.argv) > 1):
        filename = str(sys.argv[1])
        zipFilename = filename + r'_serial.zip'
        imgFilename = filename + r'_image.zip'
        print(zipFilename)
       
    #results = read_file(path)
    get_url = "http://10.90.0.15:8100/api/1/ping"  # Connected successfully

    if os.path.exists(filename+"_debug.json"):
        results = read_file(filename+"_debug.json")
        post_url = "http://ambit:bento@10.90.0.15:8100/api/1/eden/results"
    elif os.path.exists(filename+"_production.json"):
        results = read_file(filename+"_production.json")
        post_url = "http://ambit:bento@10.90.0.15:8100/api/1/eden/results"
    else:
        print("must have a json file")
        exit(0)
        
    #results = read_file('log.json')
    get_url = "http://10.90.0.15:8100/api/1/ping"  # Connected successfully
    #post_url = "http://ambit:bento@10.90.0.15:8100/api/1/eden/results" # Data was valid
    #post_url = "http://ambit:bento@10.90.0.15:8100/api/1/eden/test" # Data was valid
    # post_url = "http://10.90.0.15:8100/api/1/eden/results"
    account = {'name':'ambit', 'passwd':'bento'}

    data = {'run_results': results}
    get_from_server(get_url, account)
    temp_files = read_file(zipFilename)
    if 'BFT-' in str(zipFilename) or 'SRF-' in str(zipFilename):
        litepoint_str = 'log/litepoint.zip'
        litepoint_files = read_file(litepoint_str)
        files = {'serial.zip' : temp_files, 'litepoint.zip': litepoint_files}
    elif 'AGC-' in str(zipFilename):
        #imgFilename_str = r'log/' + imgFilename
        imgFiles = read_file(imgFilename)
        files = {'serial.zip' : temp_files, 'image.zip' : imgFiles}
    else:
        files = {'serial.zip' : temp_files}
    post_json_to_server(post_url, account, data, files)
