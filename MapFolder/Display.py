#Place a route on a labeled Brandeis map and save the image to a file.
#Usage may be any of:
#   python Display.py
#      (defaults to use BrandeisMapLabeledCropped.jpg)
#   python Display.py jpg
#      (defaults to use BrandeisMapLabeledCropped.jpg)
#   python Display.py png
#      (defaults to use BrandeisMapLabeledCropped.png)
#   python Display.py BrandeisMapLabeled.png
#   python Display.py BrandeisMapLabeled.jpg
#   python Display.py BrandeisMapLabeledCropped.png
#   python Display.py BrandeisMapLabeledCropped.jpg

import sys
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import matplotlib.image as img
import matplotlib.colors as colors
import matplotlib.cm as cmx
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import numpy as np
from itertools import cycle
cycol = cycle('bgrcmk').next

DATA = np.random.rand(5,5)
cmap = plt.cm.jet

cNorm  = colors.Normalize(vmin=np.min(DATA[:,4]), vmax=np.max(DATA[:,4]))

scalarMap = cmx.ScalarMappable(norm=cNorm,cmap=cmap)

MapName = 'BrandeisMapLabeledCropped.jpg'
if (len(sys.argv) > 1): MapName = sys.argv[1]
if (MapName=='jpg'): MapName='BrandeisMapLabeledCropped.jpg'
if (MapName=='png'): MapName='BrandeisMapLabeledCropped.png'

if ( (MapName != 'BrandeisMapLabeled.jpg') and \
     (MapName != 'BrandeisMapLabeled.png') and \
     (MapName != 'BrandeisMapLabeledCropped.jpg') and \
     (MapName !='BrandeisMapLabeledCropped.png') ):
     print ('\nIllegal map name - "' + MapName + '"\n')
     exit()

if ( (MapName=='BrandeisMapLabeled.png') or (MapName=='BrandeisMapLabeled.jpg') ):
   RouteData = 'Route.txt'
   RouteImage = 'Route.png'
if ( (MapName=='BrandeisMapLabeledCropped.png') or (MapName=='BrandeisMapLabeledCropped.jpg') ):
   RouteData = 'RouteCropped.txt'
   RouteImage = 'RouteCropped.png'

Brandeis = img.imread(MapName)

solution = open(RouteData).read().splitlines()

points = []
for line in solution:
    points.append(map(int, line.split()))

fig = plt.figure(figsize=(int(Brandeis.shape[1]/100),int(Brandeis.shape[0]/100)))
ax = fig.add_subplot(111, aspect='equal')
ax.imshow(Brandeis, alpha=1.0)

value = len(points);
step = 0.0;
count = 0;
for n in points:
    count = count + 1;
    # colorVal = scalarMap.to_rgba(DATA[idx,4])
    step = (step + 1.0) % 20.0
    if (step >= 19.0):
      value = value - step
    color = np.random.rand(3,1)
    color[0] = (value / len(points))
    color[1] = (1 - value / len(points))
    color[2] = (1 - value / len(points))
    # color = 'red'

    p = patches.FancyArrowPatch((n[0],n[1]), (n[2],n[3]),
      connectionstyle="arc3,rad=.2", 
      ec=color,
      arrowstyle='->,head_width=10,head_length=15',
      lw=5
    )
    ax.add_patch(p)
    if (count < 10):
      ax.annotate(count, xy=(n[0],n[1]), xytext=(n[2],n[3]),fontsize=30)
    # ax.arrow(n[0],n[1],n[2]-n[0],n[3]-n[1],ec=np.random.rand(3,1), lw=4, width = 5, head_width=15,head_length=15)
_ = plt.axis('off')
# plt.show()
plt.tight_layout()
plt.savefig(RouteImage, bbox_inces='tight')

print
print('BrandeisMap.Map used = ' + MapName)
print('BrandeisMap.Route BrandeisMap.data used = ' + RouteData)
print('Output image = ' + RouteImage)
print
