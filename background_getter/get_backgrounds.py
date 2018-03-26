import os
import json
import urllib.request as urlreq

def get_file_list():
    return [f for f in os.walk("./backgrounds")]

def get_top_post():
    return get_post(0)

def get_url(post_rank):
    js = urlreq.urlretrieve("https://reddit.com/r/art/.json")
    js = json.dumps(js[0])
    print(js)


def get_post(post_rank):
    post_title = "./backgrounds/" + get_url(post_rank)
    new_image = urlreq.urlretrieve("https://i.imgur.com/XUjFbWs.jpg", post_title)
    print(new_image)

def set_background(image):
    return None

def update_background():
    images = get_file_list()
    new_image = get_top_post()
    if new_image in images:
        return
    else:
        pass

get_url(0)

