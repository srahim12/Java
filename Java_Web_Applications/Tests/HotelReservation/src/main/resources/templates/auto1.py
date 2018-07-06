from bs4 import BeautifulSoup as soup
import os

def getSoup(page_html):
    page_soup = soup(page_html,"html.parser")
    return page_soup

def main():
    for filename in os.listdir(os.getcwd()):
        if(filename.split(".")[-1]=='py'):
            continue
        f = open(filename,'r+')
        soup = getSoup(f)
        for a in soup.findAll('a'):
            if('/' not in a['href']):
                a['href'] = ('/'+a['href']).replace('.html','')
        result = str(soup)
        f.close()
        f = open(filename,'w')
        f.write(result)

main()     