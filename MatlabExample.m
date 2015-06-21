DATA = csvread ('COM1-1920.csv');

%Col indexes of parameters:
run = 1;
trial = 2;
map = 3;
droids = 4;
pheromoneDecay = 5;
yellRadius = 6;
yellRelay = 7;
initialFire = 8;
milestone50 = 9;
milestone80 = 10;
done = 11;
fireLeft = 12;

temp = (DATA(:,milestone50) ~= -1);
DATA = DATA(temp,:);

temp = (DATA(:,map) == 1);
MAP1data = DATA(temp,:);

temp = (DATA(:,map) == 2);
MAP2data = DATA(temp,:);

temp = (MAP1data(:,droids) == 240);
Droids240 = MAP1data(temp,:);

temp = (MAP1data(:,droids) == 120);
Droids120 = MAP1data(temp,:);

%example scatter plot
scatter3(Droids120(:,yellRelay),Droids120(:,yellRadius),Droids120(:,milestone50))

%example 3d plot
%scatter(Droids240(:,yellRadius),Droids240(:,completelyDone));
%hold on
%scatter(Droids120(:,yellRadius),Droids120(:,completelyDone));