import pyautogui

#========================
#Image scraping & saving
#========================
# try:
#     for i in range(5):
#         for j in range(20):
#             #pc1
#             pyautogui.click(1593, 802)
#             pyautogui.hotkey('ctrl','c')
#             #pg
#             pyautogui.click(1549, 495, interval = 0.5)
#             pyautogui.hotkey('ctrl','v')
#             #pc2
#             pyautogui.click(1581, 905)
#             pyautogui.hotkey('ctrl','c')
#             pyautogui.click(1549, 495, interval = 0.5)
#             pyautogui.hotkey('ctrl','v')
#             #pc3
#             pyautogui.click(1594, 937)
#             pyautogui.hotkey('ctrl','c')
#             pyautogui.click(1549, 495, interval = 0.5)
#             pyautogui.hotkey('ctrl','v')

#             pyautogui.click(1593, 802, interval=1)
#             pyautogui.press('pagedown')
#         if input('continue? ') == 'y':
#             continue
#         else:
#             break
# except KeyboardInterrupt:
#     exit

#=================================
# extracing images from saved file
#=================================
lines = []
with open('D:\\Users\\Satvik\\Documents\\Data\\Studies\\Class 11\\Tree Scanner 23\\Code\\Git\\VVIM-App\\PDF Scraper\\data', 'r', encoding="utf8") as f:
    for line in f:
        lines.append(line.split("'"))
ln = []
for i in lines:
    try:
        for j in range(3):
            ln.append(i[1])
    except:
        pass

from spire.pdf.common import *
from spire.pdf import *

# Create a PdfDocument object
doc = PdfDocument()

# Load a PDF document
doc.LoadFromFile('D:\\Users\\Satvik\\Documents\\Data\\Studies\\Class 11\\Tree Scanner 23\\Code\\Git\\VVIM-App\\PDF Scraper\\imgs-compressed.pdf')

# Get a specific page

# Extract images from the page
images = []
step = 3
for val in range(5):
    for i in range(step*val*2, step*(val+1)*2):
        page = doc.Pages[i]
        for image in page.ExtractImages():
            images.append(image)

    # Save images to specified location with specified format extension
    index = step*val*2
    print(len(images))
    j = 1
    for image in images:
        imageFileName = f'D:\\Users\\Satvik\\Documents\\Data\\Studies\\Class 11\\Tree Scanner 23\\Code\\Git\\VVIM-App\\PDF Scraper\\imgs\\{ln[index]}-{j}.png'
        index += 1
        j += 1
        if j > 3:
            j = 1
        image.Save(imageFileName, ImageFormat.get_Png())
    
    if input('continue? ') == 'y':
        continue
    else:
        break
    
doc.Close()