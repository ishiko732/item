library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity PC is
port(clk:in std_logic;
	  JMP:in std_logic_vector(3 downto 0);
  load:in std_logic;
		PC:out std_logic_vector(3 downto 0)
);
end PC;

architecture bhv of PC is
signal q1:std_logic_vector(3 downto 0);
begin
	process(clk,q1,load)
	begin
	if(clk'event and clk='1') then
		if load='1' then
			q1<=JMP;
		else
			q1<=q1+1;
		end if;
	end if;
	PC<=q1;
	end process;
end bhv;