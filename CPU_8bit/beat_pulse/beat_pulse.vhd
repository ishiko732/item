library ieee;
use ieee.std_logic_1164.all;
entity beat_pulse is
port (clk,start:in std_logic;
  T0,T1,T2,T3:out std_logic
);
end beat_pulse;

architecture bhv of Beat_pulse is
signal q:std_logic_vector(3 downto 0):="0001";
begin
process(start,clk,q)
begin
	if (start='1') then 
		if (clk'event and clk='1') then
			q(0)<=q(3);
			q(3 downto 1)<=q(2 downto 0);
			T0<=q(0);
			T1<=q(1);
			T2<=q(2);
			T3<=q(3);
		end if;
	else
		T0<='0';
		T1<='0';
		T2<='0';
		T3<='0';
	end if;
end process;
end bhv;
