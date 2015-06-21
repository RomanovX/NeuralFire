clear all;
% load cleaned up data from mat file
% original data contians unusable droid numbers
%DATA = csvread ('COM1-1920.csv');
load('data.mat')

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

columnNames = {};
columnNames{run} = 'Run';
columnNames{trial} = 'Trial';
columnNames{map} = 'Map';
columnNames{droids} = 'Droids';
columnNames{pheromoneDecay} = 'Pheromone decay';
columnNames{yellRadius} = 'Yell radius';
columnNames{yellRelay} = 'Yell relay';
columnNames{initialFire} = 'Initial fires';
columnNames{milestone50} = '50% extinguished';
columnNames{milestone80} = '80% extinguished';
columnNames{done} = 'Everything extinguished';

mapNames = {};
mapNames{1} = 'EWI';
mapNames{2} = 'IO';

variableIndexes = [droids, pheromoneDecay, yellRadius, yellRelay];
resultIndexes = [milestone50, milestone80, done];

% turn off displaying figures
set(0,'defaultFigureVisible','off')

filenumber = 1;

for vi1 = 1:numel(variableIndexes)
    currentVariable1 = variableIndexes(vi1);
    for vi2 = (vi1+1):numel(variableIndexes)
        currentVariable2 = variableIndexes(vi2);
        for ri = 1:numel(resultIndexes)
            currentResult = resultIndexes(ri);
            
            mapIndexValues = unique(DATA(:, map));
            figure;
            plotIndex = 1;
            for mi = 1:numel(mapIndexValues)
                currentMap = mapIndexValues(mi);
                currentMapData = DATA(DATA(:, map) == currentMap, :);
                
                cleanData = currentMapData(currentMapData(:, currentResult) ~= -1, :);
                fails = currentMapData(currentMapData(:, currentResult) == -1, :);

                var1Values = unique(cleanData(:, currentVariable1));
                var2Values = unique(cleanData(:, currentVariable2));
                result = zeros(numel(var1Values), numel(var2Values));
                
                maxFails = 0;
                
                for i = 1:numel(var1Values)
                    currentVar1 = var1Values(i);
                    for j = 1:numel(var2Values)
                        currentVar2 = var2Values(j);
                        result(i, j) = mean(cleanData(cleanData(:, currentVariable1) == currentVar1 & cleanData(:, currentVariable2) == currentVar2, currentResult));
                        maxFails = numel(cleanData(cleanData(:, currentVariable1) == currentVar1 & cleanData(:, currentVariable2) == currentVar2, currentResult));
                    end
                end
                subplot(2, 2, plotIndex);
                plotIndex = plotIndex +1;
                surface(var2Values, var1Values, result);
                xlabel(columnNames{currentVariable2});
                ylabel(columnNames{currentVariable1});
                zlabel(columnNames{currentResult});
                title(mapNames{currentMap});
                view([-30 -30]);
                
                
                var1Values = unique(fails(:, currentVariable1));
                var2Values = unique(fails(:, currentVariable2));
                result = zeros(numel(var1Values), numel(var2Values));

                for i = 1:numel(var1Values)
                    currentVar1 = var1Values(i);
                    for j = 1:numel(var2Values)
                        currentVar2 = var2Values(j);
                        result(i, j) = numel(fails(fails(:, currentVariable1) == currentVar1 & fails(:, currentVariable2) == currentVar2, currentResult));
                    end
                end
                subplot(2, 2, plotIndex);
                plotIndex = plotIndex+1;
                surface(var2Values, var1Values, result);
                xlabel(columnNames{currentVariable2});
                ylabel(columnNames{currentVariable1});
                zlabel(strcat(['# fails (total # of executions: ', num2str(maxFails), ')']));
                title(strcat([mapNames{currentMap},' - # of failed executions']));
                view([-30 -30]);
            end
            
            fileTitle = strcat([num2str(filenumber), '_', columnNames{currentVariable1}, '-', columnNames{currentVariable2}, '-', columnNames{currentResult}]);
            filenumber = filenumber + 1;
            print(fileTitle,'-dpng')
            
        end
    end
end
