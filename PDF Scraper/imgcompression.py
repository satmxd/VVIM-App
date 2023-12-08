from hashlib import new
from pickletools import optimize
from PIL import Image
import os

impath = 'D:\\Users\\Satvik\\Documents\\Data\\Studies\\Class 11\\Tree Scanner 23\\Code\\Git\\VVIM-App\\PDF Scraper\\imgs\\'
savepath = 'D:\\Users\\Satvik\\Documents\\Data\\Studies\\Class 11\\Tree Scanner 23\\Code\\Git\\VVIM-App\\PDF Scraper\\imgs-compressed\\'

arr = os.listdir(impath)
print(arr)
qt = 100

for i in arr[:5]:
    img = Image.open(f'{impath}{i}')
    w, h = img.size
    new_size = (w//2, h//2)
    rs = img.resize(new_size)
    rs.save(f'{savepath}{i}', optimize = True, quality = qt)

    original_size = os.path.getsize(f'{impath}{i}')
    compressed_size = os.path.getsize(f'{savepath}{i}')

    print(f"Original Size:{original_size//1024}kb", )
    print(f"Compressed Size: {compressed_size//1024}kb")