import os

root = 'leader-card-'

for i in range(49, 65):
    os.rename(root + str(i) + '.png', 'leader-card-' + str(i-48) + '.png')
