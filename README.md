The user is given 6 different simulations that can be started, 2 for Mechanics, 2 for Modern Physics and 2 for Calculus. For each graph, the user can start, pause and stop the simulation at any time during the animation with the buttons. 

# PhysicsSimulation

## 2D Momentum

![input](https://user-images.githubusercontent.com/55627338/81462085-02f8fb80-917e-11ea-8ed7-9cda054aa478.PNG)

2 balls bounded by a pane will continously collide with themselves and the pane to produce ellastic collisions. Since no energy is assumed to be lost, they will keep bouncing forever. Information about the balls are displayed to the right. The user can change the mass of the balls(which also resizes them) as well as their velocities and starting positions.

![momentumGraph](https://user-images.githubusercontent.com/55627338/81462086-03919200-917e-11ea-97e1-1a0a60ae1f7a.PNG)

A graph plots the total momentum of the system. Its important to understand that although momentum is conserved within a system, it will not be if the system is acted on by external forces, such as the pane containing the system, hence the sudden spikes within the total momentum

## Uniform Circular Motion

![ucm](https://user-images.githubusercontent.com/55627338/81462081-012f3800-917e-11ea-8840-c497018d7c10.PNG)

A ball will continously orbit a point. By changing the period of the orbit, the mass and the radius of the ball, a user can observe how the dynamics of the motion are affected. They can be quantitavely understood via the information given to right right, or a graph.

![ucmGraph](https://user-images.githubusercontent.com/55627338/81462083-01c7ce80-917e-11ea-91b7-18899aa8bd35.PNG)

The uniform nature of the periodic movement give the pure sinusoids that are shown in the graph. The system will remain linear time invariant due to this behavior.

## Special Relativity
The user selects a speed where speed of light c = 1, and the spacecraft will compress to show the effects of length contraction, and the clock attached to the craft will show the time relative to a stationary clock to show time dilation. 

## Slit interference
![inte](https://user-images.githubusercontent.com/55627338/81462079-012f3800-917e-11ea-9e36-fe4b35b97fe2.PNG)

The user selects slit width, the distance to the screen and the wavelegnth to demonstate how to demonstates how 2 initially parallel light rays will interfere with eachother destructively to procude the black zones of no color and constructively to procude areas with color. A graph will the show a the amplitude of the light waves over the screen they are projected on.

## Trapezoidal Integration
![int](https://user-images.githubusercontent.com/55627338/81462069-fbd1ed80-917d-11ea-865a-fb0ec8fbbd6b.PNG)

The user enters a polynomial function along with the bounds of integration and the subdivisions to be used for integration. An approximate result will first be found using trapezoidal integration, where a trapezoid is made for each subdivision and the total integration area is computed as sum of areas of the trapezoidals. By chaning the subdivisions, the user can observe the change in the approximate area as it appraches the exact area, which is also computed as well as a relative error for demonstration purposes.

![intGraph](https://user-images.githubusercontent.com/55627338/81462074-0096a180-917e-11ea-9f42-24e5e4355220.PNG)

The area that is to be computed is highlighted, and the trapezoids to be used are computed as also drawn on the graph. Lastly, the user is given the ability to re-scale the graph to make the highlighted area bigger relative to the graph

## MacLauren Series
![mac](https://user-images.githubusercontent.com/55627338/81462080-012f3800-917e-11ea-8719-b627514d9709.PNG)

The user picks the function he wants to generate an approximation for

![macGraph](https://user-images.githubusercontent.com/55627338/81462087-03919200-917e-11ea-9f3e-3a9929ba6c87.PNG)

The red curves are all the total functions generated by the series, while the blue one is the superposition of all of them. We can observe how a sum of polynomials can accurerately estimate a sinusoid or exponential function
