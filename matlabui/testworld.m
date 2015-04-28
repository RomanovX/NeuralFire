
function [] = testworld()
global player_pos;
global world;
global fig;
global quit;
global ants;
ants = [4, 2;
        4, 2;
        4, 2;
        4, 4;
        4, 4];
FRAME_DELAY = .525;
world= [1, 1, 1, 1, 1, 1, 1;
        1, 0, 0, 0, 1, 0, 1;
        1, 1, 1, 0, 1, 1, 1;
        1, 0, 0, 0, 0, 0, 1;
        1, 1, 1, 0, 1, 0, 1;
        1, 0, 0, 0, 1, 0, 1;
        1, 1, 1, 1, 1, 1, 1];

player_pos = [4, 6];    
fig = figure
quit = 0;

%t = timer('StartDelay', 1, 'Period', 2, 'TasksToExecute', Inf, 'ExecutionMode', 'fixedRate');
t = timer('StartDelay', 1, 'Period', 2, 'TasksToExecute', Inf, 'ExecutionMode', 'fixedRate');
%t.StartFcn = {@my_callback_fcn, 'My start message'};
%t.StopFcn = { @my_callback_fcn, 'My stop message'};
t.TimerFcn = {@moveAnts};
start(t)
hold on;
set(fig, 'KeyReleaseFcn', @keyUpListener);
while(~quit)
    imagesc(world)
    hold on;
    playerPlot = plot(player_pos(1), player_pos(2));
    set(playerPlot, 'Marker', 'o');
    set(playerPlot, 'MarkerFaceColor', 'g');
    set(playerPlot, 'MarkerEdgeColor', 'k');
    set(playerPlot, 'MarkerSize', 10);
    set(playerPlot, 'LineStyle', 'None');
    hold on;
    antsPlot = scatter(ants(:, 1), ants(:, 2));
    set(antsPlot, 'Marker', 'o');
    set(antsPlot, 'MarkerFaceColor', 'y');
    set(antsPlot, 'MarkerEdgeColor', 'k');
    %set(antsPlot, 'MarkerSize', 5);
    %set(antsPlot, 'LineStyle', 'None');
    
    pause(FRAME_DELAY);
end
close(fig);
delete(t);

    function moveAnts(obj, event, string_arg)
        for i = 1:size(ants, 1)
            %ants(i, :);
            newcoords = [ants(i, 1) + randi(3) - 2, ants(i, 2) + randi(3) - 2];
            
            if(newcoords(1) == 0 || newcoords(2) == 0)
%                ants(i, :) = oldCoord;
                continue;
            end
            if(world(newcoords(1), newcoords(2)) == 1)
                %ants(i, :) = oldCoord;
                continue;
            end
            ants(i, :) = newcoords;
        end
        ants
        disp('ants moved');
    end
function keyUpListener(src,event)    
%function keyUpListener
    switch event.Key
      case 'a'
        player_pos(1) = player_pos(1) -1
      case 'd'
          player_pos(1) = player_pos(1) +1;
      case 'w'
          player_pos(2) = player_pos(2) +1;
      case 's'
          player_pos(2) = player_pos(2) -1;
      case 'q'
          quit = 1;
      otherwise
        
    end
    %imagesc(player_pos(1), player_pos(2), 'g');
    %hold on;
end

function renderworld(world)
figure
imagesc(world)

end
end