import random
l = []
lines = ''
with open('treedb.txt', 'r', encoding="utf8") as f:
    for line in f:
        lines += '|' + (line.removesuffix('\n'))

c = 4
x=[]
for i in lines.split('BACK'):
    x.append([i])

for i in x:
    l.append(i[0].split('|'))
with open('treedbrefined.txt', 'a') as fr:

    for tree in range(len(l)):
        name = l[tree][len(l[tree])-2].partition('/')[0]
        title = l[tree][len(l[tree])-2].partition('/')[2]
        kingdom = l[tree][len(l[tree])-10].partition(' : ')[2]
        division = l[tree][len(l[tree])-9].partition(' : ')[2]
        class_ = l[tree][len(l[tree])-8].partition(' : ')[2]
        order_ = l[tree][len(l[tree])-7].partition(' : ')[2]
        family = l[tree][len(l[tree])-6].partition(' : ')[2]
        genus = l[tree][len(l[tree])-5].partition(' : ')[2]
        species = l[tree][len(l[tree])-4].partition(' : ')[2]
        d = []
        for i in range(2, l[tree].index(l[tree][len(l[tree])-10])):
            d.append(l[tree][i])
        details = ' '.join(d)

        n = f"INSERT INTO PlantDetails (id, name, title, kingdom, division, class, order_, family, genus, species, details, url) VALUES ('{input('Enter plant code name for '+name+': ')+'0'+str(c)}', '{name}', '{title}' ,'{kingdom}', '{division}', '{class_}', '{order_}', '{family}', '{genus}', '{species}', '{details}', 'https://raw.githubusercontent.com/Amogh-Saagar/images/main/Screenshot%20from%202023-09-17%2019-04-40.png');\n"
        fr.write(n)
        c+=1

