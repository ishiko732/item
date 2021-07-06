library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity Data_Reg is
port(
	load,rst,clk:in std_logic;
	S:in std_logic_vector(1 downto 0);
	Data:in std_logic_vector(7 downto 0);
	R0,R1,R2,R3:out std_logic_vector(7 downto 0)
);
end Data_Reg;

architecture bhv of Data_Reg is
signal RO0,RO1,RO2,RO3:std_logic_vector(7 downto 0);
begin
	process(clk,rst,load)
	begin
		if rst='1' then
			RO0<=(others=>'0');
			RO1<=(others=>'0');
			RO2<=(others=>'0');
			RO3<=(others=>'0');
		elsif (clk'event and clk='1') then
			if load='1' then
				case S is
					when "00"=>RO0<=Data;
					when "01"=>RO1<=Data;
					when "10"=>RO2<=Data;
					when "11"=>RO3<=Data;
				end case;
			end if;
		end if;
	end process;
R0<=RO0;
R1<=RO1;
R2<=RO2;
R3<=RO3;
end bhv;